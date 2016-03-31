'use strict';

angular.module('consultorioOnlineAppApp').controller('GynecoobstetricBkgDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'GynecoobstetricBkg', 'Contraceptives', 'Patient',
        function($scope, $stateParams, $uibModalInstance, entity, GynecoobstetricBkg, Contraceptives, Patient) {

        $scope.gynecoobstetricBkg = entity;
        $scope.contraceptivess = Contraceptives.query();
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            GynecoobstetricBkg.get({id : id}, function(result) {
                $scope.gynecoobstetricBkg = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:gynecoobstetricBkgUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.gynecoobstetricBkg.id != null) {
                GynecoobstetricBkg.update($scope.gynecoobstetricBkg, onSaveSuccess, onSaveError);
            } else {
                GynecoobstetricBkg.save($scope.gynecoobstetricBkg, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForMenarche = {};

        $scope.datePickerForMenarche.status = {
            opened: false
        };

        $scope.datePickerForMenarcheOpen = function($event) {
            $scope.datePickerForMenarche.status.opened = true;
        };
        $scope.datePickerForBegginingSexualLife = {};

        $scope.datePickerForBegginingSexualLife.status = {
            opened: false
        };

        $scope.datePickerForBegginingSexualLifeOpen = function($event) {
            $scope.datePickerForBegginingSexualLife.status.opened = true;
        };
        $scope.datePickerForLatestPapTest = {};

        $scope.datePickerForLatestPapTest.status = {
            opened: false
        };

        $scope.datePickerForLatestPapTestOpen = function($event) {
            $scope.datePickerForLatestPapTest.status.opened = true;
        };
        $scope.datePickerForLatestMammography = {};

        $scope.datePickerForLatestMammography.status = {
            opened: false
        };

        $scope.datePickerForLatestMammographyOpen = function($event) {
            $scope.datePickerForLatestMammography.status.opened = true;
        };
}]);
