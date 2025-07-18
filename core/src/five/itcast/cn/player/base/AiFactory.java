package five.itcast.cn.player.base;

/**
 * Author by tony
 * Date on 2025/7/18.
 */
public class AiFactory {
    private static final Map<Integer, IPlayer> ais = new HashMap(2);

    public static IPlayer getInstance(int i) {
        Map<Integer, IPlayer> map = ais;
        if (map.get(Integer.valueOf(i)) == null) {
            if (i == 1) {
                map.put(Integer.valueOf(i), new ZhuBaJieAi());
            } else if (i == 2) {
                map.put(Integer.valueOf(i), new SunWuKongAi());
            }
        }
        return map.get(Integer.valueOf(i));
    }
}