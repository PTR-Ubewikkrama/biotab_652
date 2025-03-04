// Server URLs and paths
const String baseUrl = "https://bt.wavetec-qc.com/biotab_e0652_api/v1/";
const String loginPath = "login";
const String validatePath = "validate";
const String addBTDevicePath = "bt_device/add";
const String getBTDevicePath = "bt_device/get/by_code";
const String validateBTDevicePath = "bt_device/validate";
const String componentVerificationPath = "bt_device/component/verification";
const String validatePcbTestCode = "bt_device/validate/pcbTestCode";
const String validateAirPumpTestCode = "bt_device/validate/airPumpTestCode";
const String validateFanTestCode = "bt_device/validate/fanTestCode";
const String validateUIPcbTestCode = "bt_device/validate/uiPcbTestCode";
const String validateManiFoldTestCode = "bt_device/validate/manifoldTestCode";
const String validateLatchButtonTestCode =
    "bt_device/validate/latchButtonTestCode";
const String validateFrontBracketAssemblyPath =
    "bt_device/validate/valveSequenceTestCode";
const String validateOverPressureValveTestCode =
    "bt_device/validate/overPressureValveTestCode";
const String validatePowerSupplyTestCode =
    "bt_device/validate/powerSupplyTestCode";
const String validateValveCardTestCode = "bt_device/validate/valveCardTestCode";
const String deleteHHDeviceByCodePath = "bt_device/delete";

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
const String projectName = "BioTab E0652";

// Keys for secure storage
const String userGroupDB = "USER_GROUP";
const String jwtDB = "JWT";
const String resetToken = "RESET_TOKEN";
const String userNameDB = "USERNAME";

const String cameraZoomLevel = "CAMERA_ZOOM_LEVEL";
const String cameraFlashStatus = "CAMERA_FLASH_STATUS";
const String cameraScanAreaScale = "CAMERA_SCAN_AREA_SCALE";
