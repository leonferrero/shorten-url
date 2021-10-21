package com.demo.shortenurl.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;

class Base62Test {

    @Test
    void testSequence() {
        for (long i = 0; i < 10000; i++)
            System.out.println(String.format("%s %s", i, Base62.encode(i)));
    }

    @Test
    public void findRange() {

        long num = 0;
        String encoded = null;
        long decoded = 0;

        encoded = Base62.encode(num);
        decoded = Base62.decode(encoded);
        System.out.println(String.format("%s %s %s", num, encoded, decoded));

        encoded = "baaaaaaa";
        decoded = Base62.decode(encoded);
        System.out.println(String.format("%s %s", encoded, decoded));

        encoded = "89999999";
        decoded = Base62.decode(encoded);
        System.out.println(String.format("%s %s", encoded, decoded));

        encoded = "99999999";
        decoded = Base62.decode(encoded);
        System.out.println(String.format("%s %s", encoded, decoded));
        encoded = "baaaaaaaa";
        decoded = Base62.decode(encoded);
        System.out.println(String.format("%s %s", encoded, decoded));
    }

    @Test
    void testRand() {
        for (int i = 0; i < 100; i++) {
            long num = ThreadLocalRandom.current().nextLong(214818490978687L) + 3521614606208L;
            String encoded = Base62.encode(num);
            long decoded = Base62.decode(encoded);
            System.out.println(String.format("%s %s %s", num, encoded, decoded));
            Assertions.assertEquals(decoded, num);
        }
    }
}