package org.achacha.dada.engine.phonemix;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseAndSearchTest {
    @Test
    public void testSearchingInOriginal() {
        final String input = "There was madness in any direction, at any hour. If not across the Bay, then up the Golden Gate or down 101 to Los Altos or La Honda. ... You could strike sparks anywhere. There was a fantastic universal sense that whatever we were doing was right, that we were winning. ...\n" +
                "And that, I think, was the handle – that sense of inevitable victory over the forces of Old and Evil. Not in any mean or military sense; we didn't need that. Our energy would simply prevail. There was no point in fighting – on our side or theirs. We had all the momentum; we were riding the crest of a high and beautiful wave. ...\n" +
                "So now, less than five years later, you can go up on a steep hill in Las Vegas and look West, and with the right kind of eyes you can almost see the high water mark – that place where the wave finally broke and rolled back.";

        PhoneticTransformer transformer = PhoneticTransformerBuilder.builder().build();

        final String soundsLikeWave = transformer.transform("wave");
        List<Pair<String,Integer>> list = transformer.transformAndIndex(input);
        List<Integer> offsets = list.stream()
                .filter(p->p.getLeft().contains(soundsLikeWave))
                .map(Pair::getRight)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(596, 794), offsets);

        final String soundsLike1 = transformer.transform("that");
        list = transformer.transformAndIndex(input);
        offsets = list.stream()
                .filter(p->p.getLeft().contains(soundsLike1))
                .map(Pair::getRight)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(210, 249, 279, 311, 427, 773), offsets);

//        System.out.println("s{that}="+soundsLike1);
//        System.out.println("s{input}="+transformer.transform(input));
//        System.out.println("offsets="+offsets);
//        for (int offset : offsets) {
//            System.out.println(input.substring(offset-5,offset+5));
//        }
    }
}
