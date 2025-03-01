import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface Device {
    id: number;
    deviceType: string;
    deviceName: string;
    deviceMac: string;
    createdAt: string;
}

interface DeviceApiResponse {
    status: string;
    statusDescription: string;
    data: {
        devices: Device[];
        total: number;
    };
}

export interface CommonResponse {
    status: string;
    statusDescription: string;
    data: null;
}


export const deviceApi = createApi({
    reducerPath: 'deviceApi',
    baseQuery: fetchBaseQuery({
        baseUrl: config.apiBaseUrl,
        prepareHeaders: (headers) => {
            const token = localStorage.getItem("jwt") as string;
            if (!headers.has("Authorization") && token) {
                headers.set("Authorization", `Bearer ${token}`);
            }
            return headers;
        },
    }),
    tagTypes: ['deviceList'],
    endpoints: (build) => ({
        getDevices: build.mutation<DeviceApiResponse, {}>({
            query(data) {
                return {
                    url: `device/get/all`,
                    method: 'POST',
                    body: {
                        filterType: "",
                        filterValue: "",
                        status: "",
                        date: ""
                    },
                }
            },
            invalidatesTags: [{ type: 'deviceList', id: "deviceListFetch" }]
        }),
        addDevice: build.mutation<CommonResponse, {}>({
            query(body) {
                return {
                    url: `device/add`,
                    method: 'POST',
                    body,
                }
            },
            invalidatesTags: [{ type: "deviceList", id: "deviceListFetch" }]
        }),
    }),
})

export const {
    useGetDevicesMutation,
    useAddDeviceMutation,
} = deviceApi