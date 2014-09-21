package com.github.mobile.ui.domain;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;

/**
 * PageIterator的子类，只为离线做准备
 * @author sunalong
 *
 */
public class MyPageIterator<V> extends PageIterator {

    /**
     * @param request
     * @param client
     */
    public MyPageIterator(PagedRequest request, GitHubClient client) {
        super(request, client);
    }

    @Override
    public String toString() {
        return "MyPageIterator [request=" + request + ", client=" + client
                + ", nextPage=" + nextPage + ", lastPage=" + lastPage
                + ", next=" + next + ", last=" + last + ", getNextPage()="
                + getNextPage() + ", getLastPage()=" + getLastPage()
                + ", getNextUri()=" + getNextUri() + ", getLastUri()="
                + getLastUri() + ", hasNext()=" + hasNext() + ", next()="
                + next() + ", getRequest()=" + getRequest() + ", iterator()="
                + iterator() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
