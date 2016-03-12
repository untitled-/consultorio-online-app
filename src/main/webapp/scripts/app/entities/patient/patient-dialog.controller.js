'use strict';

angular.module('consultorioOnlineUiApp').controller('PatientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Patient', 'Address', 'Contact',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Patient, Address, Contact) {

        $scope.patient = entity;
		$scope.patients = Patient.query({filter: 'patients-is-null'});
        $scope.addressss = Address.query({filter: 'patients-is-null'});
        $q.all([$scope.patients.$promise, $scope.addressss.$promise]).then(function() {
            if (!$scope.patients.addresss || !$scope.patients.addresss.id) {
                return $q.reject();
            }
            return Address.get({id : $scope.patients.addresss.id}).$promise;
        }).then(function(addresss) {
            $scope.addressss.push(addresss);
        });
        $scope.contactss = Contact.query({filter: 'patients-is-null'});
        $q.all([$scope.patients.$promise, $scope.contactss.$promise]).then(function() {
            if (!$scope.patients.contacts || !$scope.patients.contacts.id) {
                return $q.reject();
            }
            return Contact.get({id : $scope.patients.contacts.id}).$promise;
        }).then(function(contacts) {
            $scope.contactss.push(contacts);
        });
        $scope.load = function(id) {
            Patient.get({id : id}, function(result) {
                $scope.patient = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:patientUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.patient.id != null) {
                Patient.update($scope.patient, onSaveSuccess, onSaveError);
            } else {
                Patient.save($scope.patient, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDateOfBirth = {};

        $scope.datePickerForDateOfBirth.status = {
            opened: false
        };

        $scope.datePickerForDateOfBirthOpen = function($event) {
            $scope.datePickerForDateOfBirth.status.opened = true;
        };
}]);
