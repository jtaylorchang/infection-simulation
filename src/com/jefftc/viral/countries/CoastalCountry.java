package com.jefftc.viral.countries;

import com.jefftc.viral.mechanics.Country;

/**
 * CoastalCountry is a type of Country that can pass the infection over air or water as well as land
 */
public class CoastalCountry extends Country {

    /**
     * Create a Country with a given name, population size, heat, dampness, and array of connections
     *
     * @param name                   the name of the Country
     * @param landConnectionNames    the array of connected Country objects via land
     * @param nonLandConnectionNames the array of connected Country objects via non-land
     * @param population             the total population size (in millions)
     * @param heat                   the heat (out of 1.0)
     * @param dampness               the dampness (out of 1.0)
     * @param wealth                 the wealth (out of 1.0)
     */
    public CoastalCountry(String name, String[] landConnectionNames, String[] nonLandConnectionNames,
                          double population, double heat, double dampness, double wealth) {
        super(name, landConnectionNames, nonLandConnectionNames, population, heat, dampness, wealth);
    }

    /**
     * Advance the Country Epoch. Should result in spreading the infection both internally
     * and potentially externally
     */
    @Override
    public void nextEpoch() {
        if (this.infectedPopulation > 0) {
            this.spreadInternally();

            if (this.nonLandConnections.size() > 0) {
                if (this.infectedPercentage > NON_LAND_THRESHOLD) {
                    // Enough people infected to cross border
                    if (RANDOM.nextDouble() < this.externalSpreadChance) {
                        // Infection spreads to another Country by air/sea
                        int connectionIndex = RANDOM.nextInt(this.nonLandConnections.size());
                        this.infect(this.nonLandConnections.get(connectionIndex));
                    }
                }
            }

            if (this.landConnections.size() > 0) {
                if (this.infectedPercentage > LAND_THRESHOLD) {
                    // Enough people infected to cross border
                    if (RANDOM.nextDouble() < this.externalSpreadChance) {
                        // Infection spreads to another Country by land
                        int connectionIndex = RANDOM.nextInt(this.landConnections.size());
                        this.infect(this.landConnections.get(connectionIndex));
                    }
                }
            }
        }
    }

    /**
     * Start the infection by infecting a single member of the population
     */
    @Override
    public void startInfection() {
        if (this.infectedPopulation == 0) {
            this.setInfectedPopulation(INITIAL_INFECTION_COUNT);
        }
    }

    /**
     * Infect a specified Country. Must be connected
     *
     * @param target the Country to infect
     */
    @Override
    public void infect(Country target) {
        if (this.isConnectedByLand(target) || this.isConnectedByNonLand(target)) {
            target.startInfection();
        }
    }

    /**
     * Spread the infection internally
     */
    @Override
    public void spreadInternally() {
        double infectedIncrease = (this.population - this.infectedPopulation)
                * this.internalSpreadChance;
        double minimumIncrease = MINIMUM_INFECTION_MULTIPLIER * this.getPopulation();
        if (infectedIncrease < minimumIncrease) {
            infectedIncrease = minimumIncrease;
        }

        this.setInfectedPopulation((int) (this.infectedPopulation + infectedIncrease));
    }

    /**
     * Closes the borders, decreasing chance of spread, controllable by player
     */
    @Override
    public void closeBorders() {

    }

}