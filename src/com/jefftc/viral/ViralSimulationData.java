package com.jefftc.viral;

import com.jefftc.engine.Command;
import com.jefftc.viral.countries.CoastalCountry;
import com.jefftc.viral.mechanics.Country;
import com.jefftc.viral.mechanics.Symptom;

import java.util.HashMap;

public class ViralSimulationData {

    public static final Command[] COMMANDS = new Command[]{
            new Command(new String[]{
                    "quit", "exit", "leave", "stop", "abandon"
            }, false)
    };

    public static final Country[] COUNTRIES = new Country[]{
            // North America
            new CoastalCountry(
                    "Canada",
                    new String[]{
                            "United States of America",
                    },
                    new String[]{

                    },
                    36.0,
                    0.25,
                    0.3,
                    0.8
            ),
            new CoastalCountry(
                    "United States of America",
                    new String[]{
                            "Canada",
                            "Mexico"
                    },
                    new String[]{

                    },
                    323.0,
                    0.75,
                    0.75,
                    0.9
            ),
            new CoastalCountry(
                    "Mexico",
                    new String[]{
                            "United States of America",
                    },
                    new String[]{

                    },
                    128.0,
                    0.85,
                    0.4,
                    0.4
            )
    };

    public static HashMap<String, Country> COUNTRY_MAP;

    public static final Symptom[] SYMPTOMS = new Symptom[]{

    };

    /**
     * Initialize the data for ViralSimulation
     */
    public static void init() {
        COUNTRY_MAP = new HashMap<>();
        for (Country country : COUNTRIES) {
            COUNTRY_MAP.put(country.getName(), country);
        }

        for (Country country : COUNTRIES) {
            country.init(COUNTRY_MAP);
        }
    }

}