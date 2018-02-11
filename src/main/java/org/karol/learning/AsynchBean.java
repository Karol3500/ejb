package org.karol.learning;

import javax.ejb.AsyncResult;
import javax.ejb.Stateless;
import java.util.concurrent.Future;

@Stateless
public class AsynchBean {
    public Future<Integer> getNumberAfterTenSeconds() {
        return new AsyncResult<Integer>(new Integer(0));
    }
}
