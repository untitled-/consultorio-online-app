'use strict';

angular.module('consultorioOnlineUiApp').controller('PatientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Patient', 'Address', 'Contact', 'HeredoFamilyBkg', 'NonPathologicBkg', 'PathologicBkg', 'GynecoobstetricBkg', 'Consultation',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Patient, Address, Contact, HeredoFamilyBkg, NonPathologicBkg, PathologicBkg, GynecoobstetricBkg, Consultation) {

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
        $scope.contacts = Contact.query();
        $scope.heredofamilybkgss = HeredoFamilyBkg.query({filter: 'patients-is-null'});
        $q.all([$scope.patients.$promise, $scope.heredofamilybkgss.$promise]).then(function() {
            if (!$scope.patients.heredoFamilyBkgs || !$scope.patients.heredoFamilyBkgs.id) {
                return $q.reject();
            }
            return HeredoFamilyBkg.get({id : $scope.patients.heredoFamilyBkgs.id}).$promise;
        }).then(function(heredoFamilyBkgs) {
            $scope.heredofamilybkgss.push(heredoFamilyBkgs);
        });
        $scope.nonpathologicbkgss = NonPathologicBkg.query({filter: 'patients-is-null'});
        $q.all([$scope.patients.$promise, $scope.nonpathologicbkgss.$promise]).then(function() {
            if (!$scope.patients.nonPathologicBkgs || !$scope.patients.nonPathologicBkgs.id) {
                return $q.reject();
            }
            return NonPathologicBkg.get({id : $scope.patients.nonPathologicBkgs.id}).$promise;
        }).then(function(nonPathologicBkgs) {
            $scope.nonpathologicbkgss.push(nonPathologicBkgs);
        });
        $scope.pathologicbkgss = PathologicBkg.query({filter: 'patients-is-null'});
        $q.all([$scope.patients.$promise, $scope.pathologicbkgss.$promise]).then(function() {
            if (!$scope.patients.pathologicBkgs || !$scope.patients.pathologicBkgs.id) {
                return $q.reject();
            }
            return PathologicBkg.get({id : $scope.patients.pathologicBkgs.id}).$promise;
        }).then(function(pathologicBkgs) {
            $scope.pathologicbkgss.push(pathologicBkgs);
        });
        $scope.gynecoobstetricbkgs = GynecoobstetricBkg.query();
        $scope.consultations = Consultation.query();
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
