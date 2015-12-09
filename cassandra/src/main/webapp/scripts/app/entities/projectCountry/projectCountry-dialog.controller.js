'use strict';

angular.module('ontimeApp').controller('ProjectCountryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectCountry',
        function($scope, $stateParams, $uibModalInstance, entity, ProjectCountry) {

        $scope.projectCountry = entity;
        $scope.load = function(id) {
            ProjectCountry.get({id : id}, function(result) {
                $scope.projectCountry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectCountryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectCountry.id != null) {
                ProjectCountry.update($scope.projectCountry, onSaveSuccess, onSaveError);
            } else {
                ProjectCountry.save($scope.projectCountry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
