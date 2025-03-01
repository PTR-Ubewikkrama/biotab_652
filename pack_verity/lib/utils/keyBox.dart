// Server URLs and paths
const String baseUrl = "https://bt.wavetec-qc.com/test_jig_api/v1/";
const String loginPath = "login";
const String validatePath = "validate";
const String addHHDevicePath = "hh_device/add";
const String getHHDevicePath = "hh_device/get/by_code";
const String validateHHDevicePath = "hh_device/validate";
const String componentVerificationPath = "hh_device/component/verification";
const String validatePcbTestCode = "hh_device/validate/pcbTestCode";
const String validateAirPumpTestCode = "hh_device/validate/airPumpTestCode";
const String validateBatteryTestCode = "hh_device/validate/batteryTestCode";
const String validateLatchButtonTestCode =
    "hh_device/validate/latchButtonTestCode";
const String validateOverPressureValveTestCode =
    "hh_device/validate/overPressureValveTestCode";
const String validatePowerSupplyTestCode =
    "hh_device/validate/powerSupplyTestCode";
const String validateValveTestCode = "hh_device/validate/valveTestCode";
const String deleteHHDeviceByCodePath = "hh_device/delete";

//final assembly paths
const String validateDeviceForFAPath = "fa/validate/device-id";
const String validateDeviceForStageTwoForFAPath =
    "fa/validate/for-stage-two/device-id";
const String getFinalAssemblyByIdPath = "fa/get";
const String addFAStepOnePath = "fa/add/step01";
const String updateFAStepOnePath = "fa/update/step01";
const String validateBladderFAPath = "fa/validate/bladder";
const String validateUPLFAPath = "fa/validate/upl-number";
const String validateUPLForStageThreeFAPath = "fa/validate/for-step-three/upl";
const String addFAStepTwoPath = "fa/add/step02";
const String updateFAStepTwoPath = "fa/update/step02";
const String validateUDIFAPath = "fa/validate/udi";
const String validateAdapterFAPath = "fa/validate/adapter";
const String addFAStepThreePath = "fa/add/step03";
const String updateFAStepThreePath = "fa/update/step03";
const String validateCartoonBoxUDIFAPath = "fa/validate/cartoon-package/udi";
const String validateCartoonBoxCartoonNumberPath =
    "fa/validate/cartoon-package/cartoon-number";
const String addCartoonPackageFAPath = "fa/add/cartoon-package";
const String updateCartoonPackageFAPath = "fa/update/cartoon-package";
const String getCartoonPackageByUDIFAPath = "fa/get/cartoon-package/";
const String getCartoonPackageByCartoonNumberFAPath =
    "fa/get/cartoon-package/by-id";

//Project Name
const String projectName = "Pack Verifier";

// Keys for secure storage
const String userGroupDB = "USER_GROUP";
const String jwtDB = "JWT";
const String resetToken = "RESET_TOKEN";
const String userNameDB = "USERNAME";

const String cameraZoomLevel = "CAMERA_ZOOM_LEVEL";
const String cameraFlashStatus = "CAMERA_FLASH_STATUS";
const String cameraScanAreaScale = "CAMERA_SCAN_AREA_SCALE";
