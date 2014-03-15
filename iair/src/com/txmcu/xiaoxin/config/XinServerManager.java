package com.txmcu.xiaoxin.config;

import com.txmcu.xiaoxin.config.XinStateManager.ConfigType;

public class XinServerManager {

	public static interface Operations {

		/**
		 * @param init callback ,close wait dialog
		 */
		public void login(boolean result);

		/**
		 * @param invoke callback
		 */
		public void configResult(ConfigType type );
	}
}
