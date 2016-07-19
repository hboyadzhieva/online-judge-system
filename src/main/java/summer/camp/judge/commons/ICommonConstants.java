package summer.camp.judge.commons;

/**
 * Common constants
 */
public interface ICommonConstants {

	

	/**
	 * Destinations
	 */
	public interface Destinations {

		/**
		 * The names of the destinations
		 */
		public interface Names {

			/**
			 * oauth
			 */
			public static final String OAUTH = "oauth";

			/**
			 * OAuth-LinkedIn
			 */
			public static final String OAUTH_LINKEDIN = "OAuth-LinkedIn";

			/**
			 * OAuth-GitHub
			 */
			public static final String OAUTH_GITHUB = "OAuth-GitHub";
		}

		/**
		 * Destination properties
		 */
		public interface Properties {

			/**
			 * URL
			 */
			public static final String URL = "URL";

			/**
			 * callback
			 */
			public static final String CALLBACK = "callback";
		}
	}

	/**
	 * Session
	 */
	public interface Session {
		/**
		 * Session properties
		 */
		public interface Properties {

			/**
			 * SuccessAuthRedirectLocation
			 */
			public static final String AUTH_REDIRECT_LOCATION = "SuccessAuthRedirectLocation";

			/**
			 * authManager
			 */
			public static final String AUTH_MANAGER = "authManager";
		}
	}

	/**
	 * Headers
	 */
	public interface Headers {
		/**
		 * Headers names
		 */
		public interface Names {

			/**
			 * Referer
			 */
			public static final String REFERER = "Referer";
		}
	}
}
