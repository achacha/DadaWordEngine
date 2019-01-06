package org.achacha.dada.engine.hyphen;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HyphenDataTest {
    private static HyphenData hyphenData = new HyphenData("resource:data/hyphen");

    @Test
    public void testFixValue() {
        assertEquals("4m1p", hyphenData.fixValue("4m1p"));
        assertEquals("a0b0c", hyphenData.fixValue("abc"));
    }

    @Test
    public void testApplyValue() {
        char[] proc = new char[5];
        proc[0] = '0';
        proc[1] = 'm';
        proc[2] = '0';
        proc[3] = 'p';
        proc[4] = '0';
        String applyValue = "4m1p";
        hyphenData.applyValue(1, proc, applyValue);

        assertEquals("[4, m, 1, p, 0]", Arrays.toString(proc));
    }

    @Test
    public void testHyphenateIgnoreLeadingTrailingAndShort() {
        assertEquals("", hyphenData.hyphenateWord(""));
        assertEquals("a", hyphenData.hyphenateWord("a"));
        assertEquals("-----", hyphenData.hyphenateWord("-----"));
        assertEquals("brick", hyphenData.hyphenateWord("brick"));
        assertEquals("in", hyphenData.hyphenateWord("in"));
        assertEquals("wall", hyphenData.hyphenateWord("wall"));
    }

    @Test
    public void testHyphenateExceptions() {
        assertEquals("as-so-ciate", hyphenData.hyphenateWord("associate"));
        assertEquals("as-so-ciates", hyphenData.hyphenateWord("associates"));
        assertEquals("dec-li-na-tion", hyphenData.hyphenateWord("declination"));
        assertEquals("oblig-a-tory", hyphenData.hyphenateWord("obligatory"));
        assertEquals("phil-an-thropic", hyphenData.hyphenateWord("philanthropic"));
        assertEquals("present", hyphenData.hyphenateWord("present"));
        assertEquals("presents", hyphenData.hyphenateWord("presents"));
        assertEquals("project", hyphenData.hyphenateWord("project"));
        assertEquals("projects", hyphenData.hyphenateWord("projects"));
        assertEquals("reci-procity", hyphenData.hyphenateWord("reciprocity"));
        assertEquals("re-cog-ni-zance", hyphenData.hyphenateWord("recognizance"));
        assertEquals("ref-or-ma-tion", hyphenData.hyphenateWord("reformation"));
        assertEquals("re-cog-ni-zance", hyphenData.hyphenateWord("recognizance"));
        assertEquals("ret-ri-bu-tion", hyphenData.hyphenateWord("retribution"));
        assertEquals("ta-ble", hyphenData.hyphenateWord("table"));
    }

    @Test
    public void testHyphenateExceptionsSyllables() {
        assertEquals(3, hyphenData.countSyllables("associate"));
        assertEquals(3, hyphenData.countSyllables("associates"));
        assertEquals(4, hyphenData.countSyllables("declination"));
        assertEquals(3, hyphenData.countSyllables("obligatory"));
        assertEquals(3, hyphenData.countSyllables("philanthropic"));
        assertEquals(1, hyphenData.countSyllables("present"));
        assertEquals(1, hyphenData.countSyllables("presents"));
        assertEquals(1, hyphenData.countSyllables("project"));
        assertEquals(1, hyphenData.countSyllables("projects"));
        assertEquals(2, hyphenData.countSyllables("reciprocity"));
        assertEquals(4, hyphenData.countSyllables("recognizance"));
        assertEquals(4, hyphenData.countSyllables("reformation"));
        assertEquals(4, hyphenData.countSyllables("recognizance"));
        assertEquals(4, hyphenData.countSyllables("retribution"));
        assertEquals(2, hyphenData.countSyllables("table"));
    }

    //@Test
    public void countSyllables() {
    // Not sure if these are all exceptions or not
    /*
        assertEquals("accouchements", 4, hyphenData.countSyllables("accouchements"));
        assertEquals("aged", 1, hyphenData.countSyllables("aged"));
        assertEquals("aida", 2, hyphenData.countSyllables("aida"));
        assertEquals("aide", 1, hyphenData.countSyllables("aide"));
        assertEquals("alkyls", 2, hyphenData.countSyllables("alkyls"));
        assertEquals("annunciatory", 6, hyphenData.countSyllables("annunciatory"));
        assertEquals("arousers", 3, hyphenData.countSyllables("arousers"));
        assertEquals("assignor", 3, hyphenData.countSyllables("assignor"));
        assertEquals("backpedalling", 4, hyphenData.countSyllables("backpedalling"));
        assertEquals("bankrollers", 3, hyphenData.countSyllables("bankrollers"));
        assertEquals("blacklead", 2, hyphenData.countSyllables("blacklead"));
        assertEquals("blessed", 1, hyphenData.countSyllables("blessed"));
        assertEquals("braize", 1, hyphenData.countSyllables("braize"));
        assertEquals("bred", 1, hyphenData.countSyllables("bred"));
        assertEquals("bregmate", 2, hyphenData.countSyllables("bregmate"));
        assertEquals("cachexias", 4, hyphenData.countSyllables("cachexias"));
        assertEquals("catwalks", 2, hyphenData.countSyllables("catwalks"));
        assertEquals("chef", 1, hyphenData.countSyllables("chef"));
        assertEquals("cherrylike", 3, hyphenData.countSyllables("cherrylike"));
        assertEquals("christian", 3, hyphenData.countSyllables("christian"));
        assertEquals("cliche", 1, hyphenData.countSyllables("cliche"));
        assertEquals("concededly", 4, hyphenData.countSyllables("concededly"));
        assertEquals("concourses", 3, hyphenData.countSyllables("concourses"));
        assertEquals("contrariety", 4, hyphenData.countSyllables("contrariety"));
        assertEquals("counterinsurgency", 6, hyphenData.countSyllables("counterinsurgency"));
        assertEquals("cuddlier", 3, hyphenData.countSyllables("cuddlier"));
        assertEquals("dee", 1, hyphenData.countSyllables("dee"));
        assertEquals("disassembly", 4, hyphenData.countSyllables("disassembly"));
        assertEquals("downer", 2, hyphenData.countSyllables("downer"));
        assertEquals("dreadfuls", 2, hyphenData.countSyllables("dreadfuls"));
        assertEquals("due", 1, hyphenData.countSyllables("due"));
        assertEquals("dumbwaiters", 3, hyphenData.countSyllables("dumbwaiters"));
        assertEquals("education", 4, hyphenData.countSyllables("education"));
        assertEquals("enfolding", 3, hyphenData.countSyllables("enfolding"));
        assertEquals("favours", 2, hyphenData.countSyllables("favours"));
        assertEquals("floc", 1, hyphenData.countSyllables("floc"));
        assertEquals("focalization", 5, hyphenData.countSyllables("focalization"));
        assertEquals("fripperies", 3, hyphenData.countSyllables("fripperies"));
        assertEquals("gravida", 3, hyphenData.countSyllables("gravida"));
        assertEquals("haberdasher", 4, hyphenData.countSyllables("haberdasher"));
        assertEquals("halide", 2, hyphenData.countSyllables("halide"));
        assertEquals("hellenize", 3, hyphenData.countSyllables("hellenize"));
        assertEquals("hodad", 2, hyphenData.countSyllables("hodad"));
        assertEquals("ide", 1, hyphenData.countSyllables("ide"));
        assertEquals("idea", 3, hyphenData.countSyllables("idea"));
        assertEquals("ideal", 2, hyphenData.countSyllables("ideal"));
        assertEquals("ideas", 2, hyphenData.countSyllables("ideas"));
        assertEquals("idee", 2, hyphenData.countSyllables("idee"));
        assertEquals("inactivation", 5, hyphenData.countSyllables("inactivation"));
        assertEquals("inbred", 2, hyphenData.countSyllables("inbred"));
        assertEquals("insufflator", 4, hyphenData.countSyllables("insufflator"));
        assertEquals("interconvert", 4, hyphenData.countSyllables("interconvert"));
        assertEquals("interdependency", 6, hyphenData.countSyllables("interdependency"));
        assertEquals("intrigants", 3, hyphenData.countSyllables("intrigants"));
        assertEquals("jaded", 2, hyphenData.countSyllables("jaded"));
        assertEquals("leal", 1, hyphenData.countSyllables("leal"));
        assertEquals("lichee", 2, hyphenData.countSyllables("lichee"));
        assertEquals("lycopod", 3, hyphenData.countSyllables("lycopod"));
        assertEquals("measurabilities", 6, hyphenData.countSyllables("measurabilities"));
        assertEquals("merchandize", 3, hyphenData.countSyllables("merchandize"));
        assertEquals("misericords", 4, hyphenData.countSyllables("misericords"));
        assertEquals("moderatenesses", 6, hyphenData.countSyllables("moderatenesses"));
        assertEquals("monkshood", 2, hyphenData.countSyllables("monkshood"));
        assertEquals("moped", 1, hyphenData.countSyllables("moped"));
        assertEquals("motivator", 4, hyphenData.countSyllables("motivator"));
        assertEquals("mudslingers", 3, hyphenData.countSyllables("mudslingers"));
        assertEquals("multivolume", 4, hyphenData.countSyllables("multivolume"));
        assertEquals("ochers", 2, hyphenData.countSyllables("ochers"));
        assertEquals("octants", 2, hyphenData.countSyllables("octants"));
        assertEquals("osar", 2, hyphenData.countSyllables("osar"));
        assertEquals("outcrowing", 3, hyphenData.countSyllables("outcrowing"));
        assertEquals("pentamidine", 4, hyphenData.countSyllables("pentamidine"));
        assertEquals("pertinacious", 5, hyphenData.countSyllables("pertinacious"));
        assertEquals("pettled", 2, hyphenData.countSyllables("pettled"));
        assertEquals("philanthropists", 4, hyphenData.countSyllables("philanthropists"));
        assertEquals("philistia", 4, hyphenData.countSyllables("philistia"));
        assertEquals("pith", 1, hyphenData.countSyllables("pith"));
        assertEquals("plication", 3, hyphenData.countSyllables("plication"));
        assertEquals("polywater", 4, hyphenData.countSyllables("polywater"));
        assertEquals("poofter", 2, hyphenData.countSyllables("poofter"));
        assertEquals("preordainment", 4, hyphenData.countSyllables("preordainment"));
        assertEquals("pressors", 2, hyphenData.countSyllables("pressors"));
        assertEquals("prevision", 3, hyphenData.countSyllables("prevision"));
        assertEquals("proustian", 3, hyphenData.countSyllables("proustian"));
        assertEquals("pyramidally", 5, hyphenData.countSyllables("pyramidally"));
        assertEquals("rampant", 2, hyphenData.countSyllables("rampant"));
        assertEquals("reckoners", 3, hyphenData.countSyllables("reckoners"));
        assertEquals("red", 1, hyphenData.countSyllables("red"));
        assertEquals("repressor", 3, hyphenData.countSyllables("repressor"));
        assertEquals("ribbed", 1, hyphenData.countSyllables("ribbed"));
        assertEquals("ritornellos", 4, hyphenData.countSyllables("ritornellos"));
        assertEquals("sailed", 1, hyphenData.countSyllables("sailed"));
        assertEquals("scandias", 3, hyphenData.countSyllables("scandias"));
        assertEquals("scotias", 3, hyphenData.countSyllables("scotias"));
        assertEquals("seance", 1, hyphenData.countSyllables("seance"));
        assertEquals("sphenoids", 2, hyphenData.countSyllables("sphenoids"));
        assertEquals("stalest", 2, hyphenData.countSyllables("stalest"));
        assertEquals("states", 1, hyphenData.countSyllables("states"));
        assertEquals("steepen", 2, hyphenData.countSyllables("steepen"));
        assertEquals("sueded", 2, hyphenData.countSyllables("sueded"));
        assertEquals("symphony", 3, hyphenData.countSyllables("symphony"));
        assertEquals("syncopating", 4, hyphenData.countSyllables("syncopating"));
        assertEquals("tactilities", 4, hyphenData.countSyllables("tactilities"));
        assertEquals("tallish", 2, hyphenData.countSyllables("tallish"));
        assertEquals("tastes", 1, hyphenData.countSyllables("tastes"));
        assertEquals("telephone", 3, hyphenData.countSyllables("telephone"));
        assertEquals("telephony", 4, hyphenData.countSyllables("telephony"));
        assertEquals("territoriality", 7, hyphenData.countSyllables("territoriality"));
        assertEquals("testes", 1, hyphenData.countSyllables("testes"));
        assertEquals("thyroxins", 3, hyphenData.countSyllables("thyroxins"));
        assertEquals("tinny", 2, hyphenData.countSyllables("tinny"));
        assertEquals("titleholders", 4, hyphenData.countSyllables("titleholders"));
        assertEquals("toted", 2, hyphenData.countSyllables("toted"));
        assertEquals("trilingual", 3, hyphenData.countSyllables("trilingual"));
        assertEquals("trousseau", 2, hyphenData.countSyllables("trousseau"));
        assertEquals("tubae", 2, hyphenData.countSyllables("tubae"));
        assertEquals("uncreates", 2, hyphenData.countSyllables("uncreates"));
        assertEquals("uncurious", 4, hyphenData.countSyllables("uncurious"));
        assertEquals("underdogs", 3, hyphenData.countSyllables("underdogs"));
        assertEquals("underfed", 2, hyphenData.countSyllables("underfed"));
        assertEquals("underutilizes", 6, hyphenData.countSyllables("underutilizes"));
        assertEquals("unscrewed", 2, hyphenData.countSyllables("unscrewed"));
        assertEquals("unswore", 2, hyphenData.countSyllables("unswore"));
        assertEquals("unsystematized", 5, hyphenData.countSyllables("unsystematized"));
        assertEquals("urea", 3, hyphenData.countSyllables("urea"));
        assertEquals("utilizes", 4, hyphenData.countSyllables("utilizes"));
        assertEquals("vacuo", 3, hyphenData.countSyllables("vacuo"));
        assertEquals("vellum", 2, hyphenData.countSyllables("vellum"));
        assertEquals("vext", 1, hyphenData.countSyllables("vext"));
        assertEquals("viceroyship", 4, hyphenData.countSyllables("viceroyship"));
        assertEquals("villein", 2, hyphenData.countSyllables("villein"));
        assertEquals("visitations", 4, hyphenData.countSyllables("visitations"));
        assertEquals("wambled", 2, hyphenData.countSyllables("wambled"));
        assertEquals("warred", 1, hyphenData.countSyllables("warred"));
*/
    }

}