'use strict';

angular.module('ontimeApp').controller('ProjectRegionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectRegion',
        function($scope, $stateParams, $uibModalInstance, entity, ProjectRegion) {

        $scope.projectRegion = entity;
        $scope.load = function(id) {
            ProjectRegion.get({id : id}, function(result) {
                $scope.projectRegion = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectRegionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectRegion.id != null) {
                ProjectRegion.update($scope.projectRegion, onSaveSuccess, onSaveError);
            } else {
                ProjectRegion.save($scope.projectRegion, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
