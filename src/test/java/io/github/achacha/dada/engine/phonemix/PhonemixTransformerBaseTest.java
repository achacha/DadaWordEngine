package io.github.achacha.dada.engine.phonemix;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhonemixTransformerBaseTest {
    @Test
    public void testTransformAndIndex() {
        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder().build();

        String input = "Hello World, this is a test!";
        List<Pair<String,Integer>> list = transformer.transformAndIndex(input);

        String transformedWords = list.stream().map(Pair::getLeft).collect(Collectors.joining(","));
        List<Integer> offsets = list.stream().map(Pair::getRight).collect(Collectors.toList());

        assertEquals("hl,wrld,zs,is,a,tst", transformedWords);
        assertEquals(Arrays.asList(0, 6, 13, 18, 21, 23), offsets);
        assertEquals('H', input.charAt(offsets.get(0)));
        assertEquals('W', input.charAt(offsets.get(1)));
        assertEquals('t', input.charAt(offsets.get(2)));
        assertEquals('i', input.charAt(offsets.get(3)));
        assertEquals('a', input.charAt(offsets.get(4)));
        assertEquals('t', input.charAt(offsets.get(5)));
    }

    @Test
    public void testReverseTransformAndIndex() {
        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder().withReverse().build();

        String input = "Hello World, this is a test!";
        List<Pair<String,Integer>> list = transformer.transformAndIndex(input);

        String transformedWords = list.stream().map(Pair::getLeft).collect(Collectors.joining(","));
        List<Integer> offsets = list.stream().map(Pair::getRight).collect(Collectors.toList());

        assertEquals("lh,dlrw,sz,si,a,tst", transformedWords);
        assertEquals(Arrays.asList(0, 6, 13, 18, 21, 23), offsets);
        assertEquals('H', input.charAt(offsets.get(0)));
        assertEquals('W', input.charAt(offsets.get(1)));
        assertEquals('t', input.charAt(offsets.get(2)));
        assertEquals('i', input.charAt(offsets.get(3)));
        assertEquals('a', input.charAt(offsets.get(4)));
        assertEquals('t', input.charAt(offsets.get(5)));
    }

    @Test
    public void testWithStartPosition() {
        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder()
                .withStartPosition(8)
                .build();

        assertEquals("", transformer.transform("PROMPT>"));
        assertEquals(0, transformer.transformAndIndex("PROMPT>").size());

        assertEquals("hl", transformer.transform("PROMPT> Hello"));

        List<Pair<String,Integer>> indexed = transformer.transformAndIndex("PROMPT> Hello");
        assertEquals(1, indexed.size());
        assertEquals("hl", indexed.get(0).getLeft());
        assertEquals(8, indexed.get(0).getRight().intValue());
    }

    @Test
    public void testWithReverse() {
        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder()
                .withReverse()
                .build();

        assertEquals("lh dlrw sz si a tst", transformer.transform("Hello World, this is a test!"));
        assertEquals("yzlf", transformer.transform("Filthy"));
        assertEquals("ytw", transformer.transform("witty"));
    }

    @Test
    public void testCompactDuplicates() {
        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder().build();

        assertEquals("sklnt", transformer.transform("succulent"));
    }

    private void assertThree(PhonemixTransformerBase transformer, String expected, String inputString) {
        char[] input = inputString.toCharArray();
        transformer.transformThree(input);
        assertArrayEquals(expected.toCharArray(), input);
    }

    @Test
    public void testCompactingThree() {
        PhonemixTransformerBase transformer = (PhonemixTransformerBase)PhoneticTransformerBuilder.builder().build();

        assertThree(transformer,"__t", "ght");
        assertThree(transformer,"_sk", "sch");
    }

    private void assertTwo(PhonemixTransformerBase transformer, String expected, String inputString) {
        char[] inputA = inputString.toCharArray();
        transformer.transformTwo(inputA);
        assertArrayEquals(expected.toCharArray(), inputA);
    }

    @Test
    public void testCompactingTwo() {
        PhonemixTransformerBase transformer = (PhonemixTransformerBase)PhoneticTransformerBuilder.builder().build();

        // c
        assertTwo(transformer,"ka", "ca");
        assertTwo(transformer,"ko", "co");
        assertTwo(transformer,"ku", "cu");

        assertTwo(transformer,"_k", "cc");
        assertTwo(transformer,"_k", "ck");

        assertTwo(transformer,"se", "ce");
        assertTwo(transformer,"si", "ci");

        assertTwo(transformer,"_C", "ch");

        // s
        assertTwo(transformer,"_S", "sh");

        // g
        assertTwo(transformer,"_f", "gh");

        // z
        assertTwo(transformer,"tz", "zz");
        assertTwo(transformer,"_z", "zh");

        // [ae]
        assertTwo(transformer,"_O", "au");
        assertTwo(transformer,"_O", "ou");

        // o
        assertTwo(transformer,"_u", "eu");

        // w
        assertTwo(transformer,"_w", "wh");
        assertTwo(transformer,"_r", "wr");

        // t
        assertTwo(transformer,"_z", "th");

        // d
        assertTwo(transformer,"_j", "dg");
    }
}
