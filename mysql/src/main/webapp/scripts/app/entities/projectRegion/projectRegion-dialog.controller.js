'use strict';

angular.module('ontimeApp').controller('ProjectRegionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ProjectRegion', 'Project', 'Region', 'Status',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ProjectRegion, Project, Region, Status) {

        $scope.projectRegion = entity;
        $scope.projects = Project.query();
        $scope.regions = Region.query({filter: 'projectregion-is-null'});
        $q.all([$scope.projectRegion.$promise, $scope.regions.$promise]).then(function() {
            if (!$scope.projectRegion.regionId) {
                return $q.reject();
            }
            return Region.get({id : $scope.projectRegion.regionId}).$promise;
        }).then(function(region) {
            $scope.regions.push(region);
        });
        $scope.statuss = Status.query({filter: 'projectregion-is-null'});
        $q.all([$scope.projectRegion.$promise, $scope.statuss.$promise]).then(function() {
            if (!$scope.projectRegion.statusId) {
                return $q.reject();
            }
            return Status.get({id : $scope.projectRegion.statusId}).$promise;
        }).then(function(status) {
            $scope.statuss.push(status);
        });
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
