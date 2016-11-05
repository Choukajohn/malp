/*
 * Copyright (C) 2016  Hendrik Borghorst & Frederik Luetkes
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gateshipone.malp.application.adapters;

/**
 * Interface used for speed optimizations on asynchronous cover loading.
 * This is necessary to load covers only at certain scroll speed to not put
 * to much load on the CPU during scrolling.
 */
public interface ScrollSpeedAdapter {
    void setScrollSpeed(int speed);
    void addImageLoadTime(long time);
    long getAverageImageLoadTime();
}
