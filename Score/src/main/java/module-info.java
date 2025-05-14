import dk.sdu.cbse.commonscore.ScoreSPI;

module Score {
    requires CommonScore;

    provides ScoreSPI with dk.sdu.cbse.score.ScoreSystem;
}