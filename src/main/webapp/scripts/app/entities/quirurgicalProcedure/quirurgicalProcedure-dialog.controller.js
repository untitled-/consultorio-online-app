'use strict';

angular.module('consultorioOnlineAppApp').controller('QuirurgicalProcedureDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuirurgicalProcedure', 'PathologicBkg',
        function($scope, $stateParams, $uibModalInstance, entity, QuirurgicalProcedure, PathologicBkg) {

        $scope.quirurgicalProcedure = entity;
        $scope.pathologicbkgs = PathologicBkg.query();
        $scope.load = function(id) {
            QuirurgicalProcedure.get({id : id}, function(result) {
                $scope.quirurgicalProcedure = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:quirurgicalProcedureUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.quirurgicalProcedure.id != null) {
                QuirurgicalProcedure.update($scope.quirurgicalProcedure, onSaveSuccess, onSaveError);
            } else {
                QuirurgicalProcedure.save($scope.quirurgicalProcedure, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
