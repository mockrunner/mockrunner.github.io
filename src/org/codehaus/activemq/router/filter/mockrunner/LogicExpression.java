/** 
 * 
 * Copyright 2004 Hiram Chirino
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 **/
package org.codehaus.activemq.router.filter.mockrunner;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * A filter performing a comparison of two objects
 * 
 * Alwin Ibba: Changed package
 * 
 * @version $Revision: 1.1 $
 */
public abstract class LogicExpression extends BinaryExpression implements BooleanExpression {

    public static BooleanExpression createOR(BooleanExpression lvalue, BooleanExpression rvalue) {
        return new LogicExpression(lvalue,rvalue) {
            protected Object evaluate(Boolean lv, Boolean rv) {
                return lv.booleanValue() || rv.booleanValue() ? Boolean.TRUE : Boolean.FALSE;
            }
            public String getExpressionSymbol() {
                return "OR";
            }
        };
    }

    public static BooleanExpression createAND(BooleanExpression lvalue, BooleanExpression rvalue) {
        return new LogicExpression(lvalue,rvalue) {
            protected Object evaluate(Boolean lv, Boolean rv) {
                return lv.booleanValue() && rv.booleanValue() ? Boolean.TRUE : Boolean.FALSE;
            }
            public String getExpressionSymbol() {
                return "AND";
            }
        };
    }

    /**
     * @param left
     * @param right
     */
    public LogicExpression(BooleanExpression left, BooleanExpression right) {
        super(left, right);
    }

    public Object evaluate(Message message) throws JMSException {
        Boolean lv = (Boolean) left.evaluate(message);
        if (lv == null) return null;
        Boolean rv = (Boolean) right.evaluate(message);
        if (rv == null) return null;
        return evaluate(lv, rv);
    }

    abstract protected Object evaluate(Boolean lv, Boolean rv);

}
