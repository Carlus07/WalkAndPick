package com.henallux.walkandpick.Utility;

import com.henallux.walkandpick.R;

public class ErrorUtility {
    public int getError(int statusCode)
    {
        switch(statusCode)
        {
            case 1 :
            {
                return R.string.errorInternet;
            }
            case 400:
            {
                return R.string.errorConnection;
            }
            case 403:
            {
                return R.string.error403;
            }
            case 500:
            {
                return R.string.error500;
            }
            case 404:
            {
                return R.string.error404;
            }
            case 408:
            {
                return R.string.error408;
            }
            case 503:
            {
                return R.string.error503;
            }
            case 401:
            {
                return R.string.error401;
            }
            default :
            {
                return R.string.error;
            }
        }
    }
}
