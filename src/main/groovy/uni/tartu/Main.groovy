package uni.tartu

import uni.tartu.discovery.Discovery
import uni.tartu.discovery.DiscoveryInitializer

import static uni.tartu.parser.Parser.parse

/**
 * author: lkokhreidze
 * date: 2/18/16
 * time: 7:33 PM
 **/

class Main {
	public static void main(String[] args) {
		def discovery = new Discovery(DiscoveryInitializer.getInitializerInstance().loadProviders(parse {
			new File(Main
				.class
				.getClassLoader()
				.getResource("test-data-1.csv")
				.toURI())
		}))
		discovery.discover()
	}
}
