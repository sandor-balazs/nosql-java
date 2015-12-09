'use strict';

angular.module('ontimeApp').controller('ProjectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'Status', 'Funding',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Project, Status, Funding) {

        $scope.project = entity;
        $scope.statuss = Status.query({filter: 'project-is-null'});
        $q.all([$scope.project.$promise, $scope.statuss.$promise]).then(function() {
            if (!$scope.project.statusId) {
                return $q.reject();
            }
            return Status.get({id : $scope.project.statusId}).$promise;
        }).then(function(status) {
            $scope.statuss.push(status);
        });
        $scope.fundings = Funding.query({filter: 'project-is-null'});
        $q.all([$scope.project.$promise, $scope.fundings.$promise]).then(function() {
            if (!$scope.project.fundingId) {
                return $q.reject();
            }
            return Funding.get({id : $scope.project.fundingId}).$promise;
        }).then(function(funding) {
            $scope.fundings.push(funding);
        });
        $scope.load = function(id) {
            Project.get({id : id}, function(result) {
                $scope.project = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.project.id != null) {
                Project.update($scope.project, onSaveSuccess, onSaveError);
            } else {
                Project.save($scope.project, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
