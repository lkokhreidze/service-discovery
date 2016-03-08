package uni.tartu

import uni.tartu.discovery.Discovery

import static uni.tartu.parser.Parser.parse
import static uni.tartu.discovery.DiscoveryInitializer.getInstance

/**
 * author: lkokhreidze
 * date: 2/18/16
 * time: 7:33 PM
 **/

class Main {
	public static void main(String[] args) {
		def discovery = new Discovery(getInstance().loadProviders(parse {
			new File(Main
				.class
				.getClassLoader()
				.getResource("test-data-2.csv")
				.toURI())
		}))
		discovery.discover()
	}
}