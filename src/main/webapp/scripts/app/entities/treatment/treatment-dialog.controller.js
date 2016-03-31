'use strict';

angular.module('consultorioOnlineAppApp').controller('TreatmentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Treatment', 'Drug', 'Consultation',
        function($scope, $stateParams, $uibModalInstance, entity, Treatment, Drug, Consultation) {

        $scope.treatment = entity;
        $scope.drugs = Drug.query();
        $scope.consultations = Consultation.query();
        $scope.load = function(id) {
            Treatment.get({id : id}, function(result) {
                $scope.treatment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:treatmentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.treatment.id != null) {
                Treatment.update($scope.treatment, onSaveSuccess, onSaveError);
            } else {
                Treatment.save($scope.treatment, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
