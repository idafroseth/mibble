/*
 * SnmpNotificationType.java
 *
 * This work is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This work is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * As a special exception, the copyright holders of this library give
 * you permission to link this library with independent modules to
 * produce an executable, regardless of the license terms of these
 * independent modules, and to copy and distribute the resulting
 * executable under terms of your choice, provided that you also meet,
 * for each linked independent module, the terms and conditions of the
 * license of that module. An independent module is a module which is
 * not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the
 * library, but you are not obligated to do so. If you do not wish to
 * do so, delete this exception statement from your version.
 *
 * Copyright (c) 2003 Per Cederberg. All rights reserved.
 */

package net.percederberg.mibble.snmp;

import java.util.ArrayList;

import net.percederberg.mibble.MibException;
import net.percederberg.mibble.MibLoaderLog;
import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.value.ObjectIdentifierValue;

/**
 * An SNMP notification type.
 *
 * @author   Per Cederberg, <per at percederberg dot net>
 * @version  2.0
 * @since    2.0
 */
public class SnmpNotificationType implements MibType {

    /**
     * The value objects.
     */
    private ArrayList objects;

    /**
     * The notification type status.
     */
    private SnmpStatus status;
    
    /**
     * The notification type description.
     */
    private String description;
    
    /**
     * The notification type reference.
     */
    private String reference;

    /**
     * Creates a new SNMP notification type.
     * 
     * @param objects        the value objects
     * @param status         the notification type status
     * @param description    the notification type description
     * @param reference      the notification type reference, or null 
     */
    public SnmpNotificationType(ArrayList objects,
                                SnmpStatus status,
                                String description,
                                String reference) {

        this.objects = objects;
        this.status = status;
        this.description = description;
        this.reference = reference;
    }

    /**
     * Initializes the MIB type. This will remove all levels of
     * indirection present, such as references to other types, and 
     * returns the basic type. No type information is lost by this 
     * operation. This method may modify this object as a 
     * side-effect, and will be called by the MIB loader.
     * 
     * @param log            the MIB loader log
     * 
     * @return the basic MIB type
     *
     * @throws MibException if an error was encountered during the
     *             initialization
     */
    public MibType initialize(MibLoaderLog log) throws MibException {
        ArrayList  list = new ArrayList();
        MibValue   value;

        for (int i = 0; i < objects.size(); i++) {
            value = (MibValue) objects.get(i);
            list.add(value.initialize(log));
        }
        this.objects = list;
        return this;
    }

    /**
     * Checks if the specified value is compatible with this type. A
     * value is compatible if and only if it is an object identifier
     * value. 
     * 
     * @param value          the value to check
     * 
     * @return true if the value is compatible, or
     *         false otherwise
     */
    public boolean isCompatible(MibValue value) {
        return value instanceof ObjectIdentifierValue;
    }

    /**
     * Returns the value objects. 
     * 
     * @return the value objects
     *
     * @see net.percederberg.mibble.MibValue
     */
    public ArrayList getObjects() {
        return objects;
    }

    /**
     * Returns the notification type status.
     * 
     * @return the notification type status
     */
    public SnmpStatus getStatus() {
        return status;
    }

    /**
     * Returns the notification type description.
     * 
     * @return the notification type description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the notification type reference.
     * 
     * @return the notification type reference, or
     *         null if no reference has been set
     */
    public String getReference() {
        return reference;
    }

    /**
     * Returns a string representation of this object.
     * 
     * @return a string representation of this object
     */
    public String toString() {
        StringBuffer  buffer = new StringBuffer();
        
        buffer.append("NOTIFICATION-TYPE (");
        buffer.append("\n  Objects: ");
        buffer.append(objects);
        buffer.append("\n  Status: ");
        buffer.append(status);
        buffer.append("\n  Description: ");
        buffer.append(description);
        if (reference != null) {
            buffer.append("\n  Reference: ");
            buffer.append(reference);
        }
        buffer.append("\n)");
        return buffer.toString();
    }
}