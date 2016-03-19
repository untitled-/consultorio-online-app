'use strict';

angular.module('consultorioOnlineUiApp').controller('PathologicBkgDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PathologicBkg', 'Addiction', 'Alergy', 'QuirurgicalProcedure', 'Trauma', 'Disease', 'Patient',
        function($scope, $stateParams, $uibModalInstance, entity, PathologicBkg, Addiction, Alergy, QuirurgicalProcedure, Trauma, Disease, Patient) {

        $scope.pathologicBkg = entity;
        $scope.addictions = Addiction.query();
        $scope.alergys = Alergy.query();
        $scope.quirurgicalprocedures = QuirurgicalProcedure.query();
        $scope.traumas = Trauma.query();
        $scope.diseases = Disease.query();
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            PathologicBkg.get({id : id}, function(result) {
                $scope.pathologicBkg = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:pathologicBkgUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pathologicBkg.id != null) {
                PathologicBkg.update($scope.pathologicBkg, onSaveSuccess, onSaveError);
            } else {
                PathologicBkg.save($scope.pathologicBkg, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
