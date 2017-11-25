import com.sun.scenario.effect.impl.state.LinearConvolveRenderState;
import model.CFG;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hiki
 * @create 2017-11-14 0:22
 */
public class Main {

	public static void main(String[] args) {
		Monitor monitor = new Monitor("cfg.txt", "input.txt");
		monitor.analyze();
	}
}
