'use strict';

angular.module('ontimeApp')
	.controller('ProjectMilestoneDeleteController', function($scope, $uibModalInstance, entity, ProjectMilestone) {

        $scope.projectMilestone = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProjectMilestone.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
