/*
 * TeleStax, Open Source Cloud Communications  Copyright 2012.
 * and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.statistics;

import java.util.Date;

import javolution.util.FastMap;

/**
*
* @author sergey vetyutnev
*
*/
public class StatDataCollection {

    private FastMap<String, StatCounterCollection> coll = new FastMap<String, StatCounterCollection>();

    public StatCounterCollection registerStatCounterCollector(String counterName, StatDataCollectorType type) {
        synchronized (this) {
            StatCounterCollection c = new StatCounterCollection(counterName, type);
            coll.put(counterName, c);
            return c;
        }
    }

//    public StatCounterCollection getStatCounterCollection(String counterName) {
//        synchronized (this) {
//            StatCounterCollection scc = coll.get(counterName);
//            return scc;
//        }
//    }

//    public StatDataCollector getStatDateCollector(String counterName, String campaignName) {
//        synchronized (this) {
//            StatCounterCollection scc = coll.get(counterName);
//            if (scc == null)
//                return null;
//
//            return scc.getStatDataCollector(campaignName);
//        }
//    }

    public void clearDeadCampaignes(Date lastTime) {
        synchronized (this) {
            for (String s : coll.keySet()) {
                StatCounterCollection d = coll.get(s);
                d.clearDeadCampaignes(lastTime);
            }
        }
    }

    public Long restartAndGet(String counterName, String campaignName, Long newVal) {
        synchronized (this) {
            StatCounterCollection scc = this.coll.get(counterName);
            if (scc != null) {
                return scc.restartAndGet(campaignName, newVal);
            } else {
                return null;
            }
        }
    }

    public void updateData(String counterName, long newVal) {
        synchronized (this) {
            StatCounterCollection scc = this.coll.get(counterName);
            if (scc != null) {
                scc.updateData(newVal);
            }
        }
    }
}