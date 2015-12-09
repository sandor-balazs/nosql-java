'use strict';

angular.module('ontimeApp')
	.controller('ProjectRegionMilestoneDeleteController', function($scope, $uibModalInstance, entity, ProjectRegionMilestone) {

        $scope.projectRegionMilestone = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProjectRegionMilestone.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
