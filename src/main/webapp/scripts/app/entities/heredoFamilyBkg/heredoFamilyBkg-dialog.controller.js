'use strict';

angular.module('consultorioOnlineUiApp').controller('HeredoFamilyBkgDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HeredoFamilyBkg', 'Disease', 'Patient',
        function($scope, $stateParams, $uibModalInstance, entity, HeredoFamilyBkg, Disease, Patient) {

        $scope.heredoFamilyBkg = entity;
        $scope.diseases = Disease.query();
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            HeredoFamilyBkg.get({id : id}, function(result) {
                $scope.heredoFamilyBkg = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:heredoFamilyBkgUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.heredoFamilyBkg.id != null) {
                HeredoFamilyBkg.update($scope.heredoFamilyBkg, onSaveSuccess, onSaveError);
            } else {
                HeredoFamilyBkg.save($scope.heredoFamilyBkg, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
