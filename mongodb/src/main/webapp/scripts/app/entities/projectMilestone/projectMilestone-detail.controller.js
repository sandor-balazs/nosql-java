'use strict';

angular.module('ontimeApp')
    .controller('ProjectMilestoneDetailController', function ($scope, $rootScope, $stateParams, entity, ProjectMilestone) {
        $scope.projectMilestone = entity;
        $scope.load = function (id) {
            ProjectMilestone.get({id: id}, function(result) {
                $scope.projectMilestone = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:projectMilestoneUpdate', function(event, result) {
            $scope.projectMilestone = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
