package com.mypoc.ClaimApplication.enums;


import org.apache.commons.lang3.StringUtils;

public enum ClaimStream
{
    HEOS("HealthEOS"),
    ALC("PHCS"),
    SAVILITY("PHCS Savility");

    private String lineOfBusiness;

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    private ClaimStream(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public static ClaimStream fromString(String claimStream)
    {
        ClaimStream retEnum = null;
        for(ClaimStream  e : ClaimStream.values())
        {
            if (StringUtils.contains(claimStream, e.toString()))
            {
                retEnum = e;
                break;
            }
        }
        return retEnum;
    }

    public static ClaimStream fromLineOfBusiness(String lob)
    {
        ClaimStream retEnum = null;
        for(ClaimStream  claimStream : ClaimStream.values())
        {
            if(claimStream.getLineOfBusiness().equalsIgnoreCase(lob))
            {
                retEnum = claimStream;
                break;
            }
        }
        return retEnum;
    }

}
