import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface PcbTest {
    testId: number;
    serialNumber: string;
    chargePortConnectStatus: boolean;
    intensityButtonStatus: boolean;
    runPauseButtonStatus: boolean;
    modeButtonStatus: boolean;
    greenLedStatus: boolean;
    blueLedStatus: boolean;
    redLedRingStatus: boolean;
    greenLedRingStatus: boolean;
    blueLedRingStatus: boolean;
    whiteLedRingStatus: boolean;
    ledRingOffStatus: boolean;
    buzzerStatus: boolean;
    latchSwitchLedOnStatus: boolean;
    latchSwitchOffStatus: boolean;
    latchSwitchLedOffStatus: boolean;
    latchSwitchOnStatus: boolean;
    chargerPortDisconnectStatus: boolean;
    pumpStatus: boolean;
    batteryChargingStatus: boolean;
    startBatteryChargingPercentage: number;
    endBatteryChargingPercentage: number;
    batteryTemperatureStatus: boolean;
    batteryTemperature: number;
    pressurePathStatus: boolean;
    startPressurePathValue: number;
    endPressurePathValue: number;
    pressureSensorStatus: boolean;
    startPressureSensorValue: number;
    endPressureSensorValue: number;
    valve01Status: boolean;
    valve01StartValue: number;
    valve01EndValue: number;
    valve02Status: boolean;
    valve02StartValue: number;
    valve02EndValue: number;
    valve03Status: boolean;
    valve03StartValue: number;
    valve03EndValue: number;
    valve04Status: boolean;
    valve04StartValue: number;
    valve04EndValue: number;
    valve05Status: boolean;
    valve05StartValue: number;
    valve05EndValue: number;
    valve06Status: boolean;
    valve06StartValue: number;
    valve06EndValue: number;
    phaseTwoValve01Status: boolean;
    phaseTwoValve01StartValue: number;
    phaseTwoValve01EndValue: number;
    phaseTwoValve02Status: boolean;
    phaseTwoValve02StartValue: number;
    phaseTwoValve02EndValue: number;
    phaseTwoValve03Status: boolean;
    phaseTwoValve03StartValue: number;
    phaseTwoValve03EndValue: number;
    phaseTwoValve04Status: boolean;
    phaseTwoValve04StartValue: number;
    phaseTwoValve04EndValue: number;
    phaseTwoValve05Status: boolean;
    phaseTwoValve05StartValue: number;
    phaseTwoValve05EndValue: number;
    phaseTwoValve06Status: boolean;
    phaseTwoValve06StartValue: number;
    phaseTwoValve06EndValue: number;
    phaseTwoPressureSensorStatus: boolean;
    phaseTwoPumpStatus: boolean;
    status: boolean;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        pcbTests: PcbTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const pcbTestApi = createApi({
    reducerPath: 'pcbTestApi',
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
    tagTypes: ['pcbTestList'],
    endpoints: (build) => ({
        getPcbTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'pcbTestList', id: "getAirPumpTests" }]
        }),
        getPcbTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'pcbTestList', id: 'getAirPumpQ' }]
        }),
    }),

})

export const {
    useGetPcbTestQQuery,
    useGetPcbTestsMutation
} = pcbTestApi