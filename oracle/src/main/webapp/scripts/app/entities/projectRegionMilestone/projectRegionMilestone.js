'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectRegionMilestone', {
                parent: 'entity',
                url: '/projectRegionMilestones',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectRegionMilestone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectRegionMilestone/projectRegionMilestones.html',
                        controller: 'ProjectRegionMilestoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectRegionMilestone');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projectRegionMilestone.detail', {
                parent: 'entity',
                url: '/projectRegionMilestone/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectRegionMilestone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectRegionMilestone/projectRegionMilestone-detail.html',
                        controller: 'ProjectRegionMilestoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectRegionMilestone');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProjectRegionMilestone', function($stateParams, ProjectRegionMilestone) {
                        return ProjectRegionMilestone.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projectRegionMilestone.new', {
                parent: 'projectRegionMilestone',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectRegionMilestone/projectRegionMilestone-dialog.html',
                        controller: 'ProjectRegionMilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    planned: null,
                                    actual: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('projectRegionMilestone', null, { reload: true });
                    }, function() {
                        $state.go('projectRegionMilestone');
                    })
                }]
            })
            .state('projectRegionMilestone.edit', {
                parent: 'projectRegionMilestone',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectRegionMilestone/projectRegionMilestone-dialog.html',
                        controller: 'ProjectRegionMilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProjectRegionMilestone', function(ProjectRegionMilestone) {
                                return ProjectRegionMilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectRegionMilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('projectRegionMilestone.delete', {
                parent: 'projectRegionMilestone',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectRegionMilestone/projectRegionMilestone-delete-dialog.html',
                        controller: 'ProjectRegionMilestoneDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProjectRegionMilestone', function(ProjectRegionMilestone) {
                                return ProjectRegionMilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectRegionMilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
