'use strict';

angular.module('consultorioOnlineUiApp').controller('DiseaseDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Disease', 'HeredoFamilyBkg', 'PathologicBkg',
        function($scope, $stateParams, $uibModalInstance, entity, Disease, HeredoFamilyBkg, PathologicBkg) {

        $scope.disease = entity;
        $scope.heredofamilybkgs = HeredoFamilyBkg.query();
        $scope.pathologicbkgs = PathologicBkg.query();
        $scope.load = function(id) {
            Disease.get({id : id}, function(result) {
                $scope.disease = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:diseaseUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.disease.id != null) {
                Disease.update($scope.disease, onSaveSuccess, onSaveError);
            } else {
                Disease.save($scope.disease, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
