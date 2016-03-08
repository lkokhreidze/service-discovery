package uni.tartu.algorithm

import static uni.tartu.utils.StringUtils.split

/**
 * author: lkokhreidze
 * date: 3/3/16
 * time: 4:58 PM
 **/

class DelimiterAnalyzer {

	private static DelimiterAnalyzer ANALYZER_INSTANCE = new DelimiterAnalyzer()

	private static final float DELIMITER_CONFIDENCE_THRESHOLD = 90.0
	private final Map<String, String> analyzedDelimiters = [:]
	private Map<?, ?> initialGrouping
	private final def knownDelimiters = [dot  : '.',
													 slash: '/']

	private DelimiterAnalyzer() {}

	public static DelimiterAnalyzer getInstance() {
		return ANALYZER_INSTANCE
	}

	public def getInitialGroups() {
		initialGrouping as Map<String, List<String>>
	}

	public String getDelimiter(String id) {
		analyzedDelimiters[id]
	}

	public void build(List<String> services) {
		initialGrouping = services
			.collect { split(it, ";") }
			.groupBy { it[0] }
			.collectEntries { k, v -> [(k): v.collect { it[1] }] }
		analyze()
	}

	/*
	 * Idea of this method is to scan all services per account, and find mostly used delimiter with 90% confidence.
	 */

	@SuppressWarnings("GroovyAssignabilityCheck")
	private void analyze() {
		initialGrouping.inject([:]) { map, k, List<String> v ->
			map << [(k): knownDelimiters.collectEntries { key, delimiter ->
				[(key): (v.count { it.contains(delimiter) } * 100) / v.size()]
			}]
		}.each { k, Map v ->
			def delimiter = null
			v.findAll { it.value > DELIMITER_CONFIDENCE_THRESHOLD }.each { delimiter = knownDelimiters[it.key] }
			analyzedDelimiters.put(k, delimiter)
		}
	}
}
