package de.moaiosbeer.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import de.moaiosbeer.helper.HttpConstants;
import de.moaiosbeer.helper.MoaHttpClient;
import de.moaiosbeer.wrapers.MutableHttpServletRequest;
 
public class BasicAuthFilter implements javax.servlet.Filter {
 
    @Override
    public void destroy() {
        
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) 
    													throws IOException, ServletException {
    	//das request umcasten
        HttpServletRequest req = (HttpServletRequest) request;
        //ein MutableHttpServletRequest(HttpServletRequest) damit erzeugen
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
        //Den Helper MoaHttpClient Inztanzieren 
        MoaHttpClient client = new MoaHttpClient();
        // Den Helper HttpConstants inztanzieren ( enthält Http Constanten)
        HttpConstants CONSTANTEN = new HttpConstants();
        
        //Http Authorization Header Inhalt erzeugen (bas64 encoding aus "username:password")
        String basicEncoding = client.getEncodeedAuthString((String)mutableRequest.getParameter("username"), (String)mutableRequest.getParameter("password"));
        // Den neuen Header ans request hängen
        mutableRequest.putHeader("Authorization", "Basic "+basicEncoding);
        chain.doFilter(mutableRequest, response);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
}


// Weitere mögliche Header Felder 
// gesendeten Content Typ als Header Info schreiben
// mutableRequest.putHeader("Content-Type", ""+MediaType.APPLICATION_JSON +", charset=utf-8");
// Accept header überscchreiben
//mutableRequest.putHeader("Accept", ""+CONSTANTEN.MIMETYPE_TEXT_JSON+","
//       								+CONSTANTEN.MIMETYPE_TEXT_HTML+","
//       								+CONSTANTEN.MIMETYPE_TEXT_PLAIN+","
//       								+CONSTANTEN.MIMETYPE_TEXT_XML);