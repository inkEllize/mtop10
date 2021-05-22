// ISetDataToService.aidl
package com.grooming.mtop10;
import com.grooming.mtop10.IServiceCallback;

interface ISetDataToService {
    void setData(int a, String s);
    void setCallBackToService(IServiceCallback callback);
    void removeCallback(IServiceCallback callback);
}