/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import java.util.Objects;

/**
 *
 * @author evan
 */
public class ListItem {
    public final int index;
    public final String label;
    public final String description;
    public final Object value;

    public ListItem(int index, String label, String description, Object value) {
        this.index = index;
        this.label = label;
        this.description = description;
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.index;
        hash = 13 * hash + Objects.hashCode(this.label);
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListItem other = (ListItem) obj;
        if (this.index != other.index) {
            return false;
        }
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.value, other.value);
    }

    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }
    
}
