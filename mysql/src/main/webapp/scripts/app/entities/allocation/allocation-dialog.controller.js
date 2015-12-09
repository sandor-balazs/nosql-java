'use strict';

angular.module('ontimeApp').controller('AllocationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Allocation', 'ProjectCountry', 'Employment', 'Skill',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Allocation, ProjectCountry, Employment, Skill) {

        $scope.allocation = entity;
        $scope.projectcountrys = ProjectCountry.query({filter: 'allocation-is-null'});
        $q.all([$scope.allocation.$promise, $scope.projectcountrys.$promise]).then(function() {
            if (!$scope.allocation.projectCountryId) {
                return $q.reject();
            }
            return ProjectCountry.get({id : $scope.allocation.projectCountryId}).$promise;
        }).then(function(projectCountry) {
            $scope.projectcountrys.push(projectCountry);
        });
        $scope.employments = Employment.query({filter: 'allocation-is-null'});
        $q.all([$scope.allocation.$promise, $scope.employments.$promise]).then(function() {
            if (!$scope.allocation.employmentId) {
                return $q.reject();
            }
            return Employment.get({id : $scope.allocation.employmentId}).$promise;
        }).then(function(employment) {
            $scope.employments.push(employment);
        });
        $scope.skills = Skill.query({filter: 'allocation-is-null'});
        $q.all([$scope.allocation.$promise, $scope.skills.$promise]).then(function() {
            if (!$scope.allocation.skillId) {
                return $q.reject();
            }
            return Skill.get({id : $scope.allocation.skillId}).$promise;
        }).then(function(skill) {
            $scope.skills.push(skill);
        });
        $scope.load = function(id) {
            Allocation.get({id : id}, function(result) {
                $scope.allocation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:allocationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.allocation.id != null) {
                Allocation.update($scope.allocation, onSaveSuccess, onSaveError);
            } else {
                Allocation.save($scope.allocation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
