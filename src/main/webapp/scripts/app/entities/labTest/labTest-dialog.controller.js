'use strict';

angular.module('consultorioOnlineUiApp').controller('LabTestDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LabTest', 'Consultation',
        function($scope, $stateParams, $uibModalInstance, entity, LabTest, Consultation) {

        $scope.labTest = entity;
        $scope.consultations = Consultation.query();
        $scope.load = function(id) {
            LabTest.get({id : id}, function(result) {
                $scope.labTest = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:labTestUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.labTest.id != null) {
                LabTest.update($scope.labTest, onSaveSuccess, onSaveError);
            } else {
                LabTest.save($scope.labTest, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
