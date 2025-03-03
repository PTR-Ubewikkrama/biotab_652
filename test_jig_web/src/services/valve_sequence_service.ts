import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface ValveSequenceTest {
    testId: number;
    deviceId: number;
    serialNumber: string;
    qrCode: string;
    physicalInspectionState: boolean | null;
    manifoldSealPressure: number;
    manifoldPressureAfter1Sec: number;
    manifoldPressureState: boolean | null;
    valve1InflationPressure: number;
    valve1State: boolean | null;
    valve3InflationPressure: number;
    valve3State: boolean | null;
    valve5InflationPressure: number;
    valve5State: boolean | null;
    valve7InflationPressure: number;
    valve7State: boolean | null;
    valve9InflationPressure: number;
    valve9State: boolean | null;
    valve11InflationPressure: number;
    valve11State: boolean | null;
    valve13InflationPressure: number;
    valve13State: boolean | null;
    valve15InflationPressure: number;
    valve15State: boolean | null;
    valve17InflationPressure: number;
    valve17State: boolean | null;
    valve19InflationPressure: number;
    valve19State: boolean | null;
    valve21InflationPressure: number;
    valve21State: boolean | null;
    valve23InflationPressure: number;
    valve23State: boolean | null;
    valve25InflationPressure: number;
    valve25State: boolean | null;
    valve27InflationPressure: number;
    valve27State: boolean | null;
    valve29InflationPressure: number;
    valve29State: boolean | null;
    valve31InflationPressure: number;
    valve31State: boolean | null;
    valve33InflationPressure: number;
    valve33State: boolean | null;
    valve35InflationPressure: number;
    valve35State: boolean | null;
    valve37InflationPressure: number;
    valve37State: boolean | null;
    valve39InflationPressure: number;
    valve39State: boolean | null;
    valve41InflationPressure: number;
    valve41State: boolean | null;
    valve43InflationPressure: number;
    valve43State: boolean | null;
    valve45InflationPressure: number;
    valve45State: boolean | null;
    valve47InflationPressure: number;
    valve47State: boolean | null;
    valve49InflationPressure: number;
    valve49State: boolean | null;
    valve51InflationPressure: number;
    valve51State: boolean | null;
    valve53InflationPressure: number;
    valve53State: boolean | null;
    valve55InflationPressure: number;
    valve55State: boolean | null;
    valve57InflationPressure: number;
    valve57State: boolean | null;
    valve59InflationPressure: number;
    valve59State: boolean | null;
    valve61InflationPressure: number;
    valve61State: boolean | null;
    valve63InflationPressure: number;
    valve63State: boolean | null;
    valve2DeflationPressure: number;
    valve2State: boolean | null;
    valve4DeflationPressure: number;
    valve4State: boolean | null;
    valve6DeflationPressure: number;
    valve6State: boolean | null;
    valve8DeflationPressure: number;
    valve8State: boolean | null;
    valve10DeflationPressure: number;
    valve10State: boolean | null;
    valve12DeflationPressure: number;
    valve12State: boolean | null;
    valve14DeflationPressure: number;
    valve14State: boolean | null;
    valve16DeflationPressure: number;
    valve16State: boolean | null;
    valve18DeflationPressure: number;
    valve18State: boolean | null;
    valve20DeflationPressure: number;
    valve20State: boolean | null;
    valve22DeflationPressure: number;
    valve22State: boolean | null;
    valve24DeflationPressure: number;
    valve24State: boolean | null;
    valve26DeflationPressure: number;
    valve26State: boolean | null;
    valve28DeflationPressure: number;
    valve28State: boolean | null;
    valve30DeflationPressure: number;
    valve30State: boolean | null;
    valve32DeflationPressure: number;
    valve32State: boolean | null;
    valve34DeflationPressure: number;
    valve34State: boolean | null;
    valve36DeflationPressure: number;
    valve36State: boolean | null;
    valve38DeflationPressure: number;
    valve38State: boolean | null;
    valve40DeflationPressure: number;
    valve40State: boolean | null;
    valve42DeflationPressure: number;
    valve42State: boolean | null;
    valve44DeflationPressure: number;
    valve44State: boolean | null;
    valve46DeflationPressure: number;
    valve46State: boolean | null;
    valve48DeflationPressure: number;
    valve48State: boolean | null;
    valve50DeflationPressure: number;
    valve50State: boolean | null;
    valve52DeflationPressure: number;
    valve52State: boolean | null;
    valve54DeflationPressure: number;
    valve54State: boolean | null;
    valve56DeflationPressure: number;
    valve56State: boolean | null;
    valve58DeflationPressure: number;
    valve58State: boolean | null;
    valve60DeflationPressure: number;
    valve60State: boolean | null;
    valve62DeflationPressure: number;
    valve62State: boolean | null;
    valve64DeflationPressure: number;
    valve64State: boolean | null;
    overallValveSequenceState: boolean | null;
    status: boolean | null;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: ValveSequenceTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const valveSequenceApi = createApi({
    reducerPath: 'valveSequenceApi',
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
    tagTypes: ['valveList'],
    endpoints: (build) => ({
        getValveSequenceTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/valve-sequence-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'valveList', id: "getValveTests" }]
        }),
        getValveSequenceTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/valve-sequence-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'valveList', id: 'getValveTestQ' }]
        }),
    }),
})

export const {
    useGetValveSequenceTestsMutation,
    useGetValveSequenceTestQQuery
} = valveSequenceApi