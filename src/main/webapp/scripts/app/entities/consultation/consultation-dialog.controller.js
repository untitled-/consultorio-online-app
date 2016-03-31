'use strict';

angular.module('consultorioOnlineAppApp').controller('ConsultationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Consultation', 'LabTest', 'Treatment', 'Symptom', 'Patient',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Consultation, LabTest, Treatment, Symptom, Patient) {

        $scope.consultation = entity;
        $scope.labtests = LabTest.query();
        $scope.treatmentss = Treatment.query({filter: 'consultations-is-null'});
        $q.all([$scope.consultations.$promise, $scope.treatmentss.$promise]).then(function() {
            if (!$scope.consultations.treatments || !$scope.consultations.treatments.id) {
                return $q.reject();
            }
            return Treatment.get({id : $scope.consultations.treatments.id}).$promise;
        }).then(function(treatments) {
            $scope.treatmentss.push(treatments);
        });
        $scope.symptoms = Symptom.query();
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            Consultation.get({id : id}, function(result) {
                $scope.consultation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:consultationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.consultation.id != null) {
                Consultation.update($scope.consultation, onSaveSuccess, onSaveError);
            } else {
                Consultation.save($scope.consultation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForConsultationDate = {};

        $scope.datePickerForConsultationDate.status = {
            opened: false
        };

        $scope.datePickerForConsultationDateOpen = function($event) {
            $scope.datePickerForConsultationDate.status.opened = true;
        };
}]);
