'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employment', {
                parent: 'entity',
                url: '/employments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.employment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employment/employments.html',
                        controller: 'EmploymentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employment.detail', {
                parent: 'entity',
                url: '/employment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.employment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employment/employment-detail.html',
                        controller: 'EmploymentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employment', function($stateParams, Employment) {
                        return Employment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employment.new', {
                parent: 'employment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/employment/employment-dialog.html',
                        controller: 'EmploymentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fte: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employment', null, { reload: true });
                    }, function() {
                        $state.go('employment');
                    })
                }]
            })
            .state('employment.edit', {
                parent: 'employment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/employment/employment-dialog.html',
                        controller: 'EmploymentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Employment', function(Employment) {
                                return Employment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('employment.delete', {
                parent: 'employment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/employment/employment-delete-dialog.html',
                        controller: 'EmploymentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Employment', function(Employment) {
                                return Employment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
