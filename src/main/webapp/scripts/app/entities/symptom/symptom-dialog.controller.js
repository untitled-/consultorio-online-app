'use strict';

angular.module('consultorioOnlineAppApp').controller('SymptomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Symptom', 'Consultation',
        function($scope, $stateParams, $uibModalInstance, entity, Symptom, Consultation) {

        $scope.symptom = entity;
        $scope.consultations = Consultation.query();
        $scope.load = function(id) {
            Symptom.get({id : id}, function(result) {
                $scope.symptom = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:symptomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.symptom.id != null) {
                Symptom.update($scope.symptom, onSaveSuccess, onSaveError);
            } else {
                Symptom.save($scope.symptom, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
