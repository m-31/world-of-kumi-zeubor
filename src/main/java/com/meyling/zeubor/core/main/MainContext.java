/* This file is part of the project "zeubor" - http://www.meyling.com/zeubor
 *
 * Copyright (C) 2014-2015  Michael Meyling
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.meyling.zeubor.core.main;

import com.meyling.zeubor.core.common.Context;

public class MainContext implements Context {

    /** Version of this kernel. */
    private static final String KERNEL_VERSION = "0.02.00";


    /** Descriptive version information of this kernel. */
    private static final String DESCRIPTIVE_KERNEL_VERSION
        = "Kumi Zeubors World - Version " + getVersion() + " ["
        + getBuildIdFromManifest() + "]";

	/**
	 * Get build information from JAR manifest file. Is also non empty string if no manifest
	 * information is available.
	 *
	 * @return  Implementation-version.
	 */
	private static String getBuildIdFromManifest() {
	    String build = Context.class.getPackage().getImplementationVersion();
	    if (build == null) {
	        build = "no regular build";
	    }
	    return build;
	}
	
	/**
	 * Get build information from JAR manifest file. Is also non empty string if no manifest
	 * information is available.
	 *
	 * @return  Implementation-version.
	 */
	private static String getVersion() {
	    String build = Context.class.getPackage().getSpecificationVersion();
	    if (build == null) {
	        build = KERNEL_VERSION;
	    }
	    return build;
	}
	
	public String getBuildId() {
	    return getBuildIdFromManifest();
	}
	
	public final String getKernelVersion() {
	    return "KERNEL_VERSION";
	}

    public final String getDescriptiveKernelVersion() {
        return DESCRIPTIVE_KERNEL_VERSION;
    }


}
