'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectCountryMilestone', {
                parent: 'entity',
                url: '/projectCountryMilestones',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectCountryMilestone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectCountryMilestone/projectCountryMilestones.html',
                        controller: 'ProjectCountryMilestoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectCountryMilestone');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projectCountryMilestone.detail', {
                parent: 'entity',
                url: '/projectCountryMilestone/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectCountryMilestone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectCountryMilestone/projectCountryMilestone-detail.html',
                        controller: 'ProjectCountryMilestoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectCountryMilestone');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProjectCountryMilestone', function($stateParams, ProjectCountryMilestone) {
                        return ProjectCountryMilestone.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projectCountryMilestone.new', {
                parent: 'projectCountryMilestone',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectCountryMilestone/projectCountryMilestone-dialog.html',
                        controller: 'ProjectCountryMilestoneDialogController',
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
                        $state.go('projectCountryMilestone', null, { reload: true });
                    }, function() {
                        $state.go('projectCountryMilestone');
                    })
                }]
            })
            .state('projectCountryMilestone.edit', {
                parent: 'projectCountryMilestone',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectCountryMilestone/projectCountryMilestone-dialog.html',
                        controller: 'ProjectCountryMilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProjectCountryMilestone', function(ProjectCountryMilestone) {
                                return ProjectCountryMilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectCountryMilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('projectCountryMilestone.delete', {
                parent: 'projectCountryMilestone',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectCountryMilestone/projectCountryMilestone-delete-dialog.html',
                        controller: 'ProjectCountryMilestoneDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProjectCountryMilestone', function(ProjectCountryMilestone) {
                                return ProjectCountryMilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectCountryMilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
