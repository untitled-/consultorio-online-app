'use strict';

angular.module('consultorioOnlineUiApp').controller('NonPathologicBkgDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'NonPathologicBkg', 'Immunization', 'Patient',
        function($scope, $stateParams, $uibModalInstance, entity, NonPathologicBkg, Immunization, Patient) {

        $scope.nonPathologicBkg = entity;
        $scope.immunizations = Immunization.query();
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            NonPathologicBkg.get({id : id}, function(result) {
                $scope.nonPathologicBkg = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:nonPathologicBkgUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.nonPathologicBkg.id != null) {
                NonPathologicBkg.update($scope.nonPathologicBkg, onSaveSuccess, onSaveError);
            } else {
                NonPathologicBkg.save($scope.nonPathologicBkg, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
