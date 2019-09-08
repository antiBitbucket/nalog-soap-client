package com.domodedovo;

import com.domodedovo.unisoft.ws.FNSNDSCAWS2;
import com.domodedovo.unisoft.ws.FNSNDSCAWS2Port;
import com.domodedovo.unisoft.ws.fnsndscaws2.request.NdsRequest2;
import com.domodedovo.unisoft.ws.fnsndscaws2.response.NdsResponse2;

public class ClientRunner {

    public static void main(String[] args) {
        FNSNDSCAWS2 fns = new FNSNDSCAWS2();
        FNSNDSCAWS2Port fnsPort = fns.getFNSNDSCAWS2Port();

        NalogClient client = new NalogClient();

        NdsRequest2 request2 = new NdsRequest2();
        request2.getNP().add(client.getNPFromConsole(args));

        NdsResponse2.NP responseNP = fnsPort.ndsRequest2(request2).getNP().get(0);
        System.out.println(client.printResponse(responseNP));
    }

}
